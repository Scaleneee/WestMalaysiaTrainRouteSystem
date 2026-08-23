package com.trainsystem.ui.screen;

import com.trainsystem.graph.TrainEdge;
import com.trainsystem.graph.TrainGraph;
import com.trainsystem.model.Station;
import com.trainsystem.ui.UIHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class SearchRouteMenu {

    //scanner and graph pass by main
    private final Scanner scanner;
    private final TrainGraph graph;

    public SearchRouteMenu(Scanner scanner, TrainGraph graph){
        this.scanner = scanner;
        this.graph = graph;
    }

    //search train route screen entrance
    public void start(){
        //display title
        UIHelper.printTitle("Search Train Route");


    }

}
