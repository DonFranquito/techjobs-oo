package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job someJob = jobData.findById(id);
        model.addAttribute("job",someJob);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors, RedirectAttributes redirectAttributes) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        //checks for errors and alerts user if name is left empty
        if (errors.hasErrors()) {
            return "new-job";
        }

        //Creates various objects based off the Ids pulled from new-job form
        //name is excluded because it is a string
        Location newLocation = jobData.getLocations().findById(jobForm.getLocationId());
        Employer newEmployer = jobData.getEmployers().findById(jobForm.getEmployerId());
        PositionType newPosition = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
        CoreCompetency newSkill = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());

        //Creates a new job variable from the data submitted by user on new-job page
        Job newJob = new Job(jobForm.getName(),newEmployer,newLocation,newPosition,newSkill);

        //Adds the new job to jobData
        jobData.add(newJob);


        //adds the job that was just created to the query string
        redirectAttributes.addAttribute("id", newJob.getId());



        return "redirect:";

    }
}
