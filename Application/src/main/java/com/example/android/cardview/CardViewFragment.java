/*
* Copyright 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.cardview;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Fragment that demonstrates how to use CardView.
 */
public class CardViewFragment extends Fragment {

    private static final String TAG = CardViewFragment.class.getSimpleName();

    // This method creates an ArrayList that has three Person objects
    // Checkout the project associated with this tutorial on Github if
    // you want to use the same images.

    private void initializeData(){
        exercises = new ArrayList<>();
        exercises.add(new Exercise("Pushups", 350, R.mipmap.pushup_icon, 100.0/350, "#5D8AA8"));
        exercises.add(new Exercise("Situps", 200, R.mipmap.situp_icon, 0.5, "#FFB347"));
        exercises.add(new Exercise("Walking", 12, R.mipmap.walking_icon, 5, "#FF6961"));
        exercises.add(new Exercise("Squats", 225, R.mipmap.squat_icon, 100.0/225, "#D1E231"));
        exercises.add(new Exercise("Pullups", 12, R.mipmap.pullup_icon, 1, "#92A1CF"));
        exercises.add(new Exercise("Stair Climbing", 12, R.mipmap.stair_climbing_icon, 100.0/15, "#8A795D"));
        exercises.add(new Exercise("Jogging", 12, R.mipmap.jogging_icon, 100.0/12, "#FF6961"));
        exercises.add(new Exercise("Swimming", 12, R.mipmap.swimming_icon, 100.0/13, "#4682B4"));
        exercises.add(new Exercise("Jumping Jacks", 10, R.mipmap.jumping_jacks_icon, 10, "#FFB347"));
        exercises.add(new Exercise("Cycling", 12, R.mipmap.cycling_icon, 100.0/12, "#836953"));
        exercises.add(new Exercise("Planking", 12, R.mipmap.planking_icon, 4, "#E2725B"));
        exercises.add(new Exercise("Leg Lifts", 12, R.mipmap.leg_lift_icon, 4, "#CB410B"));
    }

    /** The CardView widget. */
    CardView mCardView;

    int calorieGoal;
    private List<Exercise> exercises;

    /** The recycler view where we replicate and recycle the cardView to create our card list **/
    RecyclerView rv;

    /** TextView displaying the current calorie goal the user as set via the seekbar **/
    TextView mCalorieGoal;

    /**
     * SeekBar that changes the calorie goal the user sets
     */
    SeekBar mCalorieGoalSeekBar;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NotificationFragment.
     */
    public static CardViewFragment newInstance() {
        CardViewFragment fragment = new CardViewFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    public CardViewFragment() {
        initializeData();
        calorieGoal = 100;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "fragment card view created");
        return inflater.inflate(R.layout.fragment_card_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCardView = (CardView) view.findViewById(R.id.cv);
        mCalorieGoal = (TextView) view.findViewById(R.id.calorie_goal);
        mCalorieGoal.setText(100 + " Calories");
        mCalorieGoal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calorieGoal = Integer.parseInt(s.toString().split("\\s+")[0]);
                String newLabel = calorieGoal + " Calories";
                if(!mCalorieGoal.getText().toString().equals(newLabel)) {
                    mCalorieGoal.setText(calorieGoal + " Calories");
                    Log.d(TAG, String.format("New Calorie Goal : %d", calorieGoal));
                }
                updateExerciseCards();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rv = (RecyclerView)view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        RVAdapter adapter = new RVAdapter(exercises, mCalorieGoal);
        rv.setAdapter(adapter);
        rv.setItemAnimator(new SlideInUpAnimator());
        
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(llm);

        mCalorieGoalSeekBar = (SeekBar) view.findViewById(R.id.calorie_goal_seekbar);
        mCalorieGoalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                calorieGoal = progress * 5 + 50;
                Log.d(TAG, String.format("New Calorie Goal : %d", calorieGoal));
                mCalorieGoal.setText(calorieGoal + " Calories");
                updateExerciseCards();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Do nothing
            }
        });
    }

    private void updateExerciseCards(){
        for(Exercise c: exercises){
            c.updateDuration((int) Math.round(calorieGoal / c.conversionRate));
        }
        rv.setAdapter(new RVAdapter(exercises, mCalorieGoal));
        rv.invalidate();
    }
}