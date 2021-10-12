package com.example.indeed_assign;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class MyCard extends CardView{
    ArrayList<Trajectory> route;

    public MyCard(@NonNull Context context, ArrayList<Trajectory>route) {
        super(context);
        this.route=route;
    }


    public ArrayList<Trajectory> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<Trajectory> route) {
        this.route = route;
    }

    public static class Trajectory{
        int x,y;

        public Trajectory(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
