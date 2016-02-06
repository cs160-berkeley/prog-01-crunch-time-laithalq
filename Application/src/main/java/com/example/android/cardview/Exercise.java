package com.example.android.cardview;

class Exercise {
    String name, unit, color;
    int duration, iconId;
    double conversionRate;  //Converstion rate to 100 calories burned eg: 10 calories per minute of jumping jacks

    Exercise(String name, int duration, int iconId, double conversionRate, String color) {
        this.name = name;
        this.duration = duration;
        this.iconId = iconId;
        this.conversionRate = conversionRate;
        this.color = color;

        switch(this.name.toLowerCase()){
            case "swimming":
            case "jumping jacks":
            case "cycling":
            case "stair climbing":
            case "walking":
            case "planking":
            case "leg lifts":
            case "jogging":
                if(this.duration == 1)
                    this.unit = "min";
                else
                    this.unit = "mins";
                break;
            default:
                if(this.duration == 1)
                    this.unit = "rep";
                else
                    this.unit = "reps";
        }
    }

    public void updateDuration(int newDuration)
    {
        this.duration = newDuration;

        //handle plurality
        if(this.unit.equals("min") || this.unit.equals("mins")){
            if(this.duration == 1)
                this.unit = "min";
            else
                this.unit = "mins";
        }
        if(this.unit.equals("rep") || this.unit.equals("reps")){
            if(this.duration == 1)
                this.unit = "rep";
            else
                this.unit = "reps";
        }
    }
}