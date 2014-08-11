package org.craftercms.studio.controller.services.rest;

import java.util.List;
import javax.validation.Valid;

import org.craftercms.studio.api.audit.AuditService;
import org.craftercms.studio.commons.dto.Activity;
import org.craftercms.studio.commons.exception.ErrorManager;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.exceptions.StudioServerErrorCode;
import org.craftercms.studio.validation.AuditValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles the JSON API for Audit module.
 */
@Controller
@RequestMapping("/api/1/audit")
public class AuditController {


    private Logger log = LoggerFactory.getLogger(AuditController.class);
    /**
     * Audit manager instance.
     */
    private AuditService auditService;

    /**
     * Default CTOR.
     */
    public AuditController() {
    }

    @RequestMapping(
            value = "/activity/{site}",
            produces = "application/json",
            method = RequestMethod.GET)
    @ResponseBody
    public List<Activity> getActivities(
            @PathVariable final String site,
            @RequestParam(required = false) final List<String> filters
    ) throws StudioException {
        this.log.debug("Retrieving list of activities for {} using filters {}", site, filters);
        return this.auditService.getActivities(null, site, filters);
    }

    /**
     * Saves a new activity for the given site.
     *
     * @param site     Site where the activity will be save
     * @param activity Activity to be save (Form Message conversion see {@link Activity})
     * @param result   Spring MVC validation Result
     * @return The Activity that have been save
     * @throws org.craftercms.studio.commons.exception.StudioException If the given object is not valid
     */
    @RequestMapping(
            value = "/log/{site}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    @ResponseBody
    public Activity logActivity(
            @PathVariable final String site,
            @Valid @RequestBody final Activity activity,
            final BindingResult result
    ) throws StudioException {
        if (result.hasErrors()) {
            this.log.error("Unable to save a activity since is not valid");
            throw ErrorManager.createError(StudioServerErrorCode.VALIDATION_ERROR);
        } else {
            this.log.debug("Calling AuditService#logActivity with {}", activity);
            return this.auditService.logActivity(null, site, activity);
        }
    }

    @InitBinder
    protected void initBinder(final WebDataBinder binder) {
        this.log.debug("Setting Validators for AuditController");
        binder.setValidator(new AuditValidator());
    }
}
