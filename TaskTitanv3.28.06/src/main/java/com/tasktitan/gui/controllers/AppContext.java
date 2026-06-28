/**
 * This class stores a shared GUI object that can be used
 * by all controllers in the application.
 * It prevents creating multiple GUI objects and ensures
 * that all pages use the same data.
 */
package com.tasktitan.gui.controllers;



public class AppContext {

    private static DashboardController dashboardController;

    public static void setDashboardController(DashboardController controller) {
        dashboardController = controller;
    }

    public static DashboardController getDashboardController() {
        return dashboardController;
    }
}