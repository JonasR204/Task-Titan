package com.tasktitan.gui.controllers;

import database.Database;

import java.sql.Connection;


public class main {
    public static void main(String[] args){
        try (Connection con = Database.getConnection())
        {
            System.out.println("Connected!");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
