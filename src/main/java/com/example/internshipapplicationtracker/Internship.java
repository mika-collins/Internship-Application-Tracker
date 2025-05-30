/**
 * PROGRAM PURPOSE: Internship class containing the attributes, constructor, and behaviors to create a unique internship object.
 * @author Mika Collins
 * Date: 5/28/25
 */

package com.example.internshipapplicationtracker;

public class Internship {
    // Attributes
    private String companyName;
    private String roleTitle;
    private String status;
    private String dateApplied;
    private String location;
    private String compensation;
    private String type;
    private String link;
    private String portalPassword;

    /**
     * Constructor for the Internship class
     *
     * @param companyName String name of the company offering the internship
     * @param roleTitle String title of the internship role
     * @param status String status of the current internship application
     * @param dateApplied String date applied for the internship
     * @param location String location of the internship
     * @param compensation String compensation type of the internship
     * @param type String work type of the internship (ex. on-site, remote, hybrid)
     * @param link String link to the completed application
     * @param portalPassword String password used for the company application portal
     */
    public Internship(String companyName, String roleTitle, String status, String dateApplied, String location, String compensation, String type, String link, String portalPassword) {
        this.companyName = companyName;
        this.roleTitle = roleTitle;
        this.status = status;
        this.dateApplied = dateApplied;
        this.location = location;
        this.compensation = compensation;
        this.type = type;
        this.link = link;
        this.portalPassword = portalPassword;
    }

    // Getters for internship class attributes

    /**
     * Getter method to return the company name
     * @return String companyName
     */
    public String getCompanyName() { return companyName; }

    /**
     * Getter method to return the internship role title
     * @return String roleTitle
     */
    public String getRoleTitle() { return roleTitle; }

    /**
     * Getter method to return the application status
     * @return String status
     */
    public String getStatus() { return status; }

    /**
     * Getter method to return the date applied to the internship
     * @return String dateApplied
     */
    public String getDateApplied() { return dateApplied; }

    /**
     * Getter method to return the location
     * @return String location
     */
    public String getLocation() { return location; }

    /**
     * Getter method to return the compensation type
     * @return String compensation
     */
    public String getCompensation() { return compensation; }

    /**
     * Getter method to return the work type of the internship (ex. on-site, remote, hybrid)
     * @return String type
     */
    public String getType() { return type; }

    /**
     * Getter method to return the application link
     * @return String link
     */
    public String getLink() { return link; }

    /**
     * Getter method to return the application portal password
     * @return String portalPassword
     */
    public String getPortalPassword() { return portalPassword; }

    // Setters for internship class attributes

    /**
     * Setter method for the internship company name
     * @param companyName String name of the company
     */
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    /**
     * Setter method for the role title
     * @param roleTitle String role title
     */
    public void setRoleTitle(String roleTitle) { this.roleTitle = roleTitle; }

    /**
     * Setter method for the current status of the application
     * @param status String status
     */
    public void setStatus(String status) { this.status = status; }

    /**
     * Setter method for the date applied to the internship
     * @param dateApplied String date applied
     */
    public void setDateApplied(String dateApplied) { this.dateApplied = dateApplied; }

    /**
     * Setter method for the location of the internship
     * @param location String location
     */
    public void setLocation(String location) { this.location = location; }

    /**
     * Setter method for the compensation type for the internship
     * @param compensation String compensation
     */
    public void setCompensation(String compensation) { this.compensation = compensation; }

    /**
     * Setter for the work type of the internship (ex. on-site, remote, hybrid)
     * @param type String type of the internship
     */
    public void setType(String type) { this.type = type; }

    /**
     * Setter for the link to the internship application
     * @param link String link to application
     */
    public void setLink(String link) { this.link = link; }

    /**
     * Setter for the application portal password
     * @param portalPassword String portal password
     */
    public void setPortalPassword(String portalPassword) { this.portalPassword = portalPassword; }

    /**
     * Method to return a CSV-safe, comma-separated string of the internship class attributes
     * @return a CSV-safe formatted string
     */
    public String toCSV() {
        return String.join(",",
                escape(companyName),
                escape(roleTitle),
                escape(status),
                escape(dateApplied),
                escape(location),
                escape(compensation),
                escape(type),
                escape(link),
                escape(portalPassword));
    }

    /**
     * Method to handle escaping for quotes and commas from user input
     * @param attribute the input attribute string to escape for CSV output
     * @return a CSV-safe version of the input attribute string
     */
    public String escape(String attribute) {
        if (attribute.contains(",") || attribute.contains("\"") || attribute.contains("\n")) {
            attribute = attribute.replace("\"", "\"\""); // escaping the internal quotes
            return "\"" + attribute + "\""; // wrap attribute in quotes
        }
        return attribute;
    }
}
