package com.capgemini.capskills.controllers.api;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.capskills.managers.interfaces.base.IBaseManager;
import com.capgemini.capskills.models.Grading;

/**
 * Implements the following api requests
 * Get all the grades: 						GET URL/grading/
 * 
 * 
 * Add a grading system:					POST URL/grading/?collaboratorgrade=collaboratorgrade&targetgrade=targetgrade&actualgrade=actualgrade
 * 
 * Get a specific user with his id:			GET URL/users/{id}
 * Delete a specific user with his id:		DELETE URL/users/{id}
 * Add a user writing all the attributes:	POST 'URL/users/?firstname=firstname&lastname=lastname&email=email&password=password&referent=referent'
 * Update an user with all attributes:		PUT 'URL/users/?firstname=firstname&lastname=lastname&email=email&password=password&referent=referent'
 * Bind a skill to a user:					PUT 'URL/users/{userId}/{skillId}'
 * 
 * @author quentin.prigent
 *
 */

@RestController
@RequestMapping("/grading")
public class GradingApiController {

	@Autowired
	private IBaseManager<Grading> manager;
	
	/**
	 * Method get all grades
	 * @return
	 */
	
    @RequestMapping(value="/", method=RequestMethod.GET)
    public List<Grading> getAll() {
        return this.manager.getAll();
    }
	
    /**
     * Method to get a grading system with a specific id
     * @param id
     * @param response
     * @return
     */
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Grading get(@PathVariable Integer id, HttpServletResponse response) {
        Grading entity = this.manager.getById(id);

        if (entity == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        return entity;
    }
    
    /**
     * Delete a specific grading system with its id
     * @param id
     * @return
     */
	
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public Grading delete(@PathVariable Integer id) {
        Grading type = this.manager.getById(id);

        if (type != null) {
            this.manager.delete(type);
        }

        return type;
    }
    
    /**
     * Add a grading system
     * @param collaboratorgrade
     * @param targetgrade
     * @param actualgrade
     * @return
     */
    
    @RequestMapping(value="/", method=RequestMethod.POST)
    public Grading create(@RequestParam Integer collaboratorgrade, @RequestParam Integer targetgrade, @RequestParam Integer actualgrade) {
    	Grading entity = new Grading();

        entity.setCollaboratorgrade(collaboratorgrade);
        entity.setTargetgrade(targetgrade);
        entity.setActualgrade(actualgrade);

        this.manager.create(entity);

        return entity;
    }
    

    /**
     * Update a collaborator grade of a grading system
     * @param response
     * @param id
     * @param collaboratorgrade
     * @return
     */

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public Grading updateCollaboratorGrade(HttpServletResponse response, @PathVariable Integer id, @RequestParam Integer collaboratorgrade, @RequestParam Integer actualgrade, @RequestParam Integer targetgrade) {
    	Grading entity = this.manager.getById(id);

        if (entity == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } if(collaboratorgrade != null && !collaboratorgrade.equals(entity.getCollaboratorgrade())) {
            entity.setCollaboratorgrade(collaboratorgrade);
        } if(actualgrade != null && !actualgrade.equals(entity.getActualgrade())) {
        	entity.setActualgrade(actualgrade);
        } if(targetgrade != null && !targetgrade.equals(entity.getTargetgrade())) {
        	entity.setTargetgrade(targetgrade);
        } else {
            response.setStatus(418);
        }

        this.manager.update(entity);
        
        return entity;
    }


//    @RequestMapping(value="/fill", method=RequestMethod.POST)
//    public List<Grading> fill() {
//        for (Integer collaboratorgrade : Arrays.asList(3, 4, 5, 6, 7)) {
//        	Grading type = new Grading(collaboratorgrade);
//            this.manager.create(type);
//        }
//
//        return this.getAll();
//    }   

}

