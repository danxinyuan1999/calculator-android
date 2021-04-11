package com.dan.mycalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

public class MainActivity extends Activity {

    //堆栈的设置
    Stack<Double> StackNum = new Stack<Double>();//数栈
    Stack<Character> StackSymbol = new Stack<Character>();//符号栈
    TextView show;
    String s = "";//总的计算表达式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        show = findViewById(R.id.text_view);
        Button butEqual = findViewById(R.id.button_equal);//等于号
        butEqual.setOnClickListener(v -> {
            s = show.getText().toString();//获取总运算表达式
            try {
                s += "=";
                show.setText(s);
                double r = calculate(s);//开始计算
                String ru = "";
                ru = String.format("%.10f", r); //保留10位小数
                if (ru.indexOf(".") > 0) { //去0
                    //正则表达
                    ru = ru.replaceAll("0+?$", "");//去掉后面无用的零
                    ru = ru.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
                }
                show.setText(ru);//显示结果
            } catch (Exception e) {
                show.setText("");
                Toast.makeText(MainActivity.this, "输入错误", Toast.LENGTH_LONG).show();
            }
        });

        Button butClear = findViewById(R.id.button_clear);//清空
        butClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.setText("");
            }
        });

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

    }

    //计算函数
    public double calculate(String formula) {

        char CurrentOperator;
        int ct = 0;
        double x = 0, y = 0, number = 0;
        String dp = "";
        StackSymbol.push('#');
        char c = formula.charAt(ct++);//从运算表达式读入一个字符
        while (c != '=') {//如果不是等于号
            //如果当前字符不是操作运算符，即是数字
            if (c != '+' && c != '-' && c != '*' && c != '/' && c != '(' && c != ')' && c != '=') {
                while (c == '.' || (c >= '0' && c <= '9')) {//如果是数字或者小数点，那么一直读取，并存取到dp里面
                    dp += c;
                    c = formula.charAt(ct++);
                }
                number = Double.parseDouble(dp);//将dp转化为double类型
                dp = "";
                StackNum.push(number);//将该数入栈
            } else {
                //如果是操作运算符，那么判断优先级后进行操作，
                //当前运算符优先级级小于上一个运算符优先级，将当前运算符入栈
                //当前运算符优先级级等于上一个运算符优先级，即（）那么直接删除）
                //当前运算符优先级级大于上一个运算符优先级，取得数栈的前两个数，并与当前运算符进行运算，后将结果入数栈
                switch (Judge(StackSymbol.peek(), c)) {
                    case -1:
                        StackSymbol.push(c);
                        c = formula.charAt(ct++);
                        break;
                    case 0:
                        StackSymbol.pop();
                        c = formula.charAt(ct++);
                        break;
                    case 1:
                        CurrentOperator = StackSymbol.pop();
                        y = StackNum.pop();
                        x = StackNum.pop();
                        StackNum.push(Operate(x, y, CurrentOperator));
                        break;
                }
            }
        }
        //如果是=，那么会直接退出，所以还需要进行一次运算，才能得出最后结果
        CurrentOperator = StackSymbol.pop();
        y = StackNum.pop();
        x = StackNum.pop();
        StackNum.push(Operate(x, y, CurrentOperator));
        double result = StackNum.peek();
        StackNum.clear();
        StackSymbol.clear();

        return result;
    }

    //判断优先级
    private int Judge(char a, char b) {
        int c = 0;
        switch (a) {
            case '+':
            case '-':
                if (b == '*' || b == '/' || b == '(')
                    c = -1;
                else
                    c = 1;
                break;

            case '*':
            case '/':
                if (b == '(')
                    c = -1;
                else
                    c = 1;
                break;

            case '(':
                if (b == ')')
                    c = 0;
                else
                    c = -1;
                break;
            case ')':
                c = 1;
                break;
            case '#':
                if (b == '#')
                    c = 0;
                else
                    c = -1;
                break;
        }
        return c;
    }

    //根据运算符进行运算
    private double Operate(double x, double y, char c) {
        double z = 0;
        switch (c) {
            case '+':
                z = x + y;
                break;
            case '-':
                z = x - y;
                break;
            case '*':
                z = x * y;
                break;
            case '/':
                z = x / y;
                break;
        }
        return z;
    }

}