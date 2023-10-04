/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.instrumentation.annotations.WithSpan
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.samples.petclinic.visit.VisitRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Antoine Rey
 */
@Controller
class OwnerController(
        val owners: OwnerRepository,
        val visits: VisitRepository,
        @Autowired val openTelemetry: OpenTelemetry
) {

    val VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm"

    private val tracer: Tracer = openTelemetry.getTracer(OwnerController::class.java.name, "0.1.0")

    @InitBinder
    fun setAllowedFields(dataBinder: WebDataBinder) {
        dataBinder.setDisallowedFields("id")
    }

    @WithSpan
    @GetMapping("/owners/new")
    fun initCreationForm(model: MutableMap<String, Any>): String {
        val owner = Owner()
        model["owner"] = owner
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM
    }

    @WithSpan
    @PostMapping("/owners/new")
    fun processCreationForm(@Valid owner: Owner, result: BindingResult): String {
        return if (result.hasErrors()) {
            VIEWS_OWNER_CREATE_OR_UPDATE_FORM
        } else {
            val span = tracer.spanBuilder("save owner").startSpan()
            owners.save(owner)
            span.end()
            "redirect:/owners/" + owner.id
        }
    }

    @WithSpan
    @GetMapping("/owners/find")
    fun initFindForm(model: MutableMap<String, Any>): String {
        model["owner"] = Owner()
        return "owners/findOwners"
    }

    @WithSpan
    @GetMapping("/owners")
    fun processFindForm(
            owner: Owner,
            result: BindingResult,
            model: MutableMap<String, Any>
    ): String {
        // find owners by last name
        val span = tracer.spanBuilder("find owners").startSpan()
        val results = owners.findByLastName(owner.lastName)
        span.end()

        return when {
            results.isEmpty() -> {
                // no owners found
                result.rejectValue("lastName", "notFound", "not found")
                "owners/findOwners"
            }
            results.size == 1 -> {
                // 1 owner found
                "redirect:/owners/" + results.first().id
            }
            else -> {
                // multiple owners found
                model["selections"] = results
                "owners/ownersList"
            }
        }
    }

    @WithSpan
    @GetMapping("/owners/{ownerId}/edit")
    fun initUpdateOwnerForm(@PathVariable("ownerId") ownerId: Int, model: Model): String {
        val span = tracer.spanBuilder("find owner").startSpan()
        val owner = owners.findById(ownerId)
        span.end()

        model.addAttribute(owner)
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM
    }

    @WithSpan
    @PostMapping("/owners/{ownerId}/edit")
    fun processUpdateOwnerForm(
            @Valid owner: Owner,
            result: BindingResult,
            @PathVariable("ownerId") ownerId: Int
    ): String {
        return if (result.hasErrors()) {
            VIEWS_OWNER_CREATE_OR_UPDATE_FORM
        } else {
            owner.id = ownerId
            val span = tracer.spanBuilder("save owner").startSpan()
            this.owners.save(owner)
            span.end()

            "redirect:/owners/{ownerId}"
        }
    }

    /**
     * Custom handler for displaying an owner.
     *
     * @param ownerId the ID of the owner to display
     * @return the view
     */
    @WithSpan
    @GetMapping("/owners/{ownerId}")
    fun showOwner(@PathVariable("ownerId") ownerId: Int, model: Model): String {
        val span = tracer.spanBuilder("find owner").startSpan()
        val owner = this.owners.findById(ownerId)
        span.end()

        for (pet in owner.getPets()) {
            val visitsSpan = tracer.spanBuilder("find visits").startSpan()
            pet.visits = visits.findByPetId(pet.id!!)
            visitsSpan.end()
        }
        model.addAttribute(owner)
        return "owners/ownerDetails"
    }
}
