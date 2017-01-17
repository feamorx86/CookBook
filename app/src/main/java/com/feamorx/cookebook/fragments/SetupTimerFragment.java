package com.feamorx.cookebook.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.feamorx.cookebook.R;
import com.feamorx.cookebook.model.Recipe;

import java.util.ArrayList;

/**
 * Created by Home on 18.01.2017.
 */

public class SetupTimerFragment extends Fragment {

    private View [] numberViews;

    private View buttonStart;
    private View buttonStopWatch;
    private View buttonFromRecipe;

    private View buttonRemoveTime;
    private TextView hourText, minuteText, secondText;

    private int timeInSeconds = 0;

    private ArrayList<Integer> timeArray = new ArrayList<>();

    private Recipe recipe;

    public static SetupTimerFragment getInstance(Recipe recipe) {
        SetupTimerFragment fragment = new SetupTimerFragment();
        fragment.recipe = recipe;
        return fragment;
    }

    public SetupTimerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timer_fragment, container, false);
//        private View [] numberViews;

        buttonStart = view.findViewById(R.id.timer_button_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startTimer();
                Toast.makeText(getContext(), "Start timer for: "+timeInSeconds, Toast.LENGTH_SHORT).show();
            }
        });
        buttonStopWatch = view.findViewById(R.id.timer_button_stopwatch);
        buttonStopWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startStopWatch();
                Toast.makeText(getContext(), "Start Stop Watch", Toast.LENGTH_SHORT).show();
            }
        });
        buttonFromRecipe = view.findViewById(R.id.timer_button_from_recipe);
        buttonFromRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeFromRecipe(true);
            }
        });

        buttonRemoveTime = view.findViewById(R.id.button_time_remove);
        buttonRemoveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeTimerSymbol();
            }
        });
        hourText = (TextView) view.findViewById(R.id.timer_hours_value);
        minuteText = (TextView) view.findViewById(R.id.timer_minutes_value);
        secondText = (TextView) view.findViewById(R.id.timer_second_value);

        int [] ids = new int[]{R.id.timer_number_0,
                R.id.timer_number_1, R.id.timer_number_2, R.id.timer_number_3,
                R.id.timer_number_4, R.id.timer_number_5, R.id.timer_number_6,
                R.id.timer_number_7, R.id.timer_number_8, R.id.timer_number_9};
        numberViews = new View[10];
        for(int i=0; i<10; i++) {
            numberViews[i] = view.findViewById(ids[i]);
            numberViews[i].setTag(Integer.valueOf(i));
            numberViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int value = (Integer) view.getTag();
                    addTimerSymbol(value);
                }
            });
        }

        timeInSeconds = 0;
        timeArray.clear();
        printTime(0,0,0);

        if (recipe != null) {
            timeFromRecipe(false);
        } else {
            fromSeconds();
            updateArray();
        }
        return view;
    }

    private void fromSeconds() {
        int hours = timeInSeconds / (SECONDS_IN_HOUR);
        int minutes = (timeInSeconds - (hours * SECONDS_IN_HOUR)) / 60;
        int seconds = (timeInSeconds - (hours * SECONDS_IN_HOUR) - minutes * 60);
        timeArray.clear();
        if (seconds > 9) {
            int s_0 = seconds % 10;
            int s_10 = ((seconds - s_0) / 10) % 10;
            timeArray.add(s_0);
            timeArray.add(s_10);
        } else {
            timeArray.add(0);
            timeArray.add(seconds);
        }
        if (minutes > 9) {
            int s_0 = minutes % 10;
            int s_10 = ((minutes - s_0) / 10) % 10;
            timeArray.add(s_0);
            timeArray.add(s_10);
        } else {
            timeArray.add(0);
            timeArray.add(seconds);
        }
        if (hours > 99) {
            hours = 99;
        }
        if (hours > 9) {
            int s_0 = hours % 10;
            int s_10 = ((hours - s_0) / 10) % 10;
            timeArray.add(s_0);
            timeArray.add(s_10);
        } else {
            timeArray.add(0);
            timeArray.add(hours);
        }
    }


    private void removeTimerSymbol() {
        trimTimeArray();
        if (timeArray.size() > 0) {
            timeArray.remove(timeArray.size()-1);
        }
        updateArray();
    }

    private void trimTimeArray() {
        while(timeArray.size() > 0) {
            if (timeArray.get(0) == 0)
                timeArray.remove(0);
            else
                break;
        }

    }

    private void updateArray() {
        int hour = 0, minute = 0, second = 0;
        trimTimeArray();
        if (timeArray.size() > 0) {
            if (timeArray.size() >= 2) {
                int sec_0 = timeArray.get(timeArray.size()-1);
                int sec_10 = timeArray.get(timeArray.size()-2);
                second = sec_10 * 10 + sec_0;
                if (timeArray.size() >= 4) {
                    int min_0 = timeArray.get(timeArray.size()-3);
                    int min_10 = timeArray.get(timeArray.size()-4);
                    minute = min_10 * 10 + min_0;
                    if (timeArray.size() >= 6) {
                        int hour_0 = timeArray.get(timeArray.size()-5);
                        int hour_10 = timeArray.get(timeArray.size()-6);
                        hour = hour_10 * 10 + hour_0;
                    } else if (timeArray.size() == 5) {
                        hour = timeArray.get(0);
                    }
                } else if (timeArray.size() == 3) {
                    minute = timeArray.get(0);
                }
            } else {
                second = timeArray.get(0);
            }
        }
        timeInSeconds = hour * SECONDS_IN_HOUR + minute * 60 + second;
        printTime(hour, minute, second);
    }

    private void addTimerSymbol(int number) {
        trimTimeArray();
        if (timeArray.size() <6) {
            timeArray.add(number);
        }
        updateArray();
    }

    private void printTime(int hour, int minute, int second) {
        hourText.setText(String.format("%02d", hour));
        minuteText.setText(String.format("%02d", minute));
        secondText.setText(String.format("%02d", second));
    }


    private void timeFromRecipe(boolean showNotification) {
        if (recipe != null && recipe.getMinutes() != null && recipe.getMinutes().intValue() >= 0) {
            timeInSeconds = recipe.getMinutes() * 60;
            fromSeconds();
            updateArray();
        } else {
            if (showNotification) {
                Toast.makeText(getContext(), "В рецепте время не задано", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static final int SECONDS_IN_HOUR = 60*60;

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
