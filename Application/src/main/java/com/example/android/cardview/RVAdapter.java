package com.example.android.cardview;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ExerciseViewHolder>{

    List<Exercise> exercises;
    TextWatcher exerciseDurationChangedListener;
    TextView calorieGoal;

    RVAdapter(List<Exercise> exercises, TextView calorieGoal){
        this.exercises = exercises;
        exerciseDurationChangedListener = new ExerciseDurationChangeListener();
        this.calorieGoal = calorieGoal;
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView exerciseName;
        TextView exerciseUnit;
        ImageView exerciseIcon;
        EditText exerciseDuration;
        ExerciseDurationChangeListener customListener;

        ExerciseViewHolder(View itemView, ExerciseDurationChangeListener customListener) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            exerciseName = (TextView)itemView.findViewById(R.id.exercise_name);
            exerciseUnit = (TextView)itemView.findViewById(R.id.exercise_unit);
            exerciseIcon = (ImageView)itemView.findViewById(R.id.exercise_icon);
            exerciseDuration = (EditText)itemView.findViewById(R.id.exercise_duration);
            this.customListener = customListener;
        }

        void bindCustomListener(){
            exerciseDuration.addTextChangedListener(customListener);
        }
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exercise_card, viewGroup, false);
        return new ExerciseViewHolder(v, new ExerciseDurationChangeListener());
    }

    private class ExerciseDurationChangeListener implements TextWatcher {
        private final String TAG = ExerciseDurationChangeListener.class.getSimpleName();
        private double conversionRate;
        private TextView calorieGoal;

        public void intialize(double rate, TextView calorieGoal) {
            this.conversionRate = rate;
            this.calorieGoal = calorieGoal;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            Log.d(TAG, "text changed");
            int newCalorieGoal = (int) Math.round(conversionRate * Integer.parseInt(charSequence.toString()));
            Log.d(TAG, String.format("New Calorie Goal : %d", newCalorieGoal));
            calorieGoal.setText(newCalorieGoal + " Calories");
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder exerciseViewHolder, int i) {
        exerciseViewHolder.exerciseName.setText(exercises.get(i).name);
        exerciseViewHolder.exerciseName.setTextColor(Color.parseColor(exercises.get(i).color));
        exerciseViewHolder.exerciseDuration.setText(exercises.get(i).duration + "");
        exerciseViewHolder.exerciseUnit.setText(exercises.get(i).unit);
        exerciseViewHolder.exerciseIcon.setImageResource(exercises.get(i).iconId);
        exerciseViewHolder.customListener.intialize(exercises.get(i).conversionRate, calorieGoal);
        exerciseViewHolder.bindCustomListener();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
