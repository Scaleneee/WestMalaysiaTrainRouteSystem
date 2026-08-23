package com.trainsystem;

import com.trainsystem.graph.TrainGraph;

import java.util.Scanner;
import java.util.Set;

import com.trainsystem.model.Station;
import com.trainsystem.ui.UIHelper;
import com.trainsystem.ui.screen.RouteMenu;
import com.trainsystem.ui.screen.StationMenu;

public class Main {

    // declare a private TrainGraph use to store the stations and routes
    private static final TrainGraph GRAPH = new TrainGraph();

    // declare a scanner obj used to get user input
    private static final Scanner SCANNER = new Scanner(System.in);

    static {
        // declare all vertices (Station)
        Station kkb = new Station("KKB", "");
        Station kls = new Station("KLS", "KL Sentral");
        Station bpr = new Station("BPR", "");
        Station pad = new Station("PAD", "");
        Station ara = new Station("ARA", "");
        Station srm = new Station("SRM", "");
        Station als = new Station("ALS", "Alor Setar");
        Station gms = new Station("GMS", "Gemas");
        Station seg = new Station("SEG", "Segamat");
        Station klv = new Station("KLV", "Kluang");
        Station jhb = new Station("JHB", "Johor Bahru");
        Station spg = new Station("SPG", "");
        Station but = new Station("BUT", "Butterworth");
        Station tas = new Station("TAS", "Taiping");
        Station bm  = new Station("BM", "Bukit Mertajam");
        Station iph = new Station("IPH", "Ipoh");
        Station tgs = new Station("TGS", "");


    }

    public static void main(String[] args) {
        // start the application
        mainMenu();
    }

    private static void mainMenu() {

        // ask user input
        int choice = 0;
        do {
            // display title
            UIHelper.printTitle("Train Route Management System");

            // display menu choices
            System.out.println("1. Manage Train Stations");
            System.out.println("2. Manage Train Routes");
            System.out.println("3. Search Train Routes");
            System.out.println("4. Display Train Network");
            System.out.println("0. Exit\n");

            // get user choice
            choice = UIHelper.getMenuChoice(SCANNER, 4);

            switch (choice) {
                case 1:
                    // manage train stations
                    new StationMenu(SCANNER, GRAPH).start();
                    break;
                case 2:
                    // manage train routes
                    new RouteMenu(SCANNER, GRAPH).start();
                    break;
                case 3:
                    // search train routes
                    break;
                case 4:
                    // display train network
                    break;
                default:
                    // choice = 0, exit
                    return;
            }
        } while (choice != 0);
    }


}
