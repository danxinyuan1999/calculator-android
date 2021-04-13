package com.dan.mycalculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class MainActivity extends Activity {

    //堆栈的设置
    private Stack<Double> StackNum = new Stack<Double>();//数栈
    private Stack<Character> StackSymbol = new Stack<Character>();//符号栈
    private TextView show;
    private String s = "";//总的计算表达式
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        show = findViewById(R.id.text_view);
        Button butEqual = findViewById(R.id.button_equal);//等于号
        butEqual.setOnClickListener(v -> {
            s = show.getText().toString();//获取总运算表达式
            try {
                Symbols symbols = new Symbols();
                double res = symbols.eval(s);
                show.setText(String.valueOf(res));
            } catch (SyntaxException e) {
                Toast.makeText(MainActivity.this, "错误！", Toast.LENGTH_SHORT).show();
            }

        });

        Button butClear = findViewById(R.id.button_clear);//清空
        butClear.setOnClickListener(v -> show.setText(""));

        Button butMinus = findViewById(R.id.button_minus);//减法
        butMinus.setOnClickListener(v -> show.append("-"));

        Button butPlus = findViewById(R.id.button_add);//加法
        butPlus.setOnClickListener(v -> show.append("+"));

        Button butMul = findViewById(R.id.button_mult);//乘法
        butMul.setOnClickListener(v -> show.append("*"));

        Button butDiv = findViewById(R.id.button_divide);//除法
        butDiv.setOnClickListener(v -> show.append("/"));

        Button butPoint = findViewById(R.id.button_dot);//小数点
        butPoint.setOnClickListener(v -> show.append("."));

        Button butPercent = findViewById(R.id.button_percent);
        butPercent.setOnClickListener(v -> show.append("%"));

        //数字0-9
        Button butZero = findViewById(R.id.button_0);
        butZero.setOnClickListener(v -> show.append("0"));

        Button butOne = findViewById(R.id.button_1);
        butOne.setOnClickListener(v -> show.append("1"));

        Button butTwo = findViewById(R.id.button_2);
        butTwo.setOnClickListener(v -> show.append("2"));

        Button butThree = findViewById(R.id.button_3);
        butThree.setOnClickListener(v -> show.append("3"));

        Button butFour = findViewById(R.id.button_4);
        butFour.setOnClickListener(v -> show.append("4"));

        Button butFive = findViewById(R.id.button_5);
        butFive.setOnClickListener(v -> show.append("5"));


        Button butSix = findViewById(R.id.button_6);
        butSix.setOnClickListener(v -> show.append("6"));

        Button butSeven = findViewById(R.id.button_7);
        butSeven.setOnClickListener(v -> show.append("7"));

        Button butEight = findViewById(R.id.button_8);
        butEight.setOnClickListener(v -> show.append("8"));

        Button butNine = findViewById(R.id.button_9);
        butNine.setOnClickListener(v -> show.append("9"));


        Button butDelete = findViewById(R.id.button_ac);//删除
        butDelete.setOnClickListener(v -> {
            String s = show.getText().toString();
            if (s.equals("")) {
                show.setText("");
            } else {
                s = s.substring(0, s.length() - 1);
                show.setText(s);
            }
        });

        if (savedInstanceState != null) {
            String position = savedInstanceState.getString("key");
            TextView tv = findViewById(R.id.text_view);
            tv.setText(position);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outstate) {
        TextView tv = findViewById(R.id.text_view);
        String num = tv.getText().toString();
        super.onSaveInstanceState(outstate);
        outstate.putString("key", num);
    }


}