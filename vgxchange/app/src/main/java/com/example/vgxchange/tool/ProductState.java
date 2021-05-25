package com.example.vgxchange.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductState {

    public enum State {

        available,
        unavailable;

        State() {
        }
    }

    public static List<State> stateList = new ArrayList <State>(Arrays.asList(State.values()));
}