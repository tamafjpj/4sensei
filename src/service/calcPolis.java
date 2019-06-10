package service;

import model.TableOfName;
import model.Token;

import java.util.*;

class calcPolis {
    private List<Token> polis;
    private List<TableOfName> var_table = new LinkedList<>();
    private Stack<Token> stack = new Stack<>();
    calcPolis(List<Token> polis){
        this.polis = polis;
    }

    //вычисление полиса
   public void calc_polis(){
        Token var = new Token();
        boolean jmp = false;

        for (int i = 0; i < polis.size(); i++){
            Token item = polis.get(i);
            if(item.type.equals("VAR") || item.type.equals("NUM") || item.type.equals("JUMP")){
                stack.add(item);
            }

            if(item.type.equals("ASSIGN_OP")){
                var = stack.pop();
            }
            if(item.type.equals("OP")){
                ops(var.token, stack.pop().token, stack.pop().token, item.token);
            }

            if (item.type.equals("LOG_OP")){
                jmp = log_ops(item.token, stack.pop().token, stack.pop().token);
            }


            if (item.type.equals("IF")){

                System.out.println("\n" + "( "+!jmp+" )" );
                for (TableOfName itemm : var_table){
                    System.out.println(itemm.getName() + " " + itemm.getValue());
                }

                int jump = Integer.parseInt(stack.pop().token);

                if(jump > i){
                    if(!jmp){
                        i = jump;
                        System.out.println("\nstep i\n");
                    }
                }
                else {
                    if(jmp){
                        i = jump;
                        System.out.println("\nstep i\n");
                    }
                }
            }
        }

        System.out.println("\n");
        for (TableOfName itemm : var_table){
            System.out.println(itemm.getName() + " " + itemm.getValue());
        }
    }

    //вычисление арифметического выражения
    private void ops(String var, String first, String second, String op){
        double v1, v2;
        try {
            v1 = Double.parseDouble(first);
        }catch (Exception var2){
            v1 = find_var(first);
        }
        try {
            v2 = Double.parseDouble(second);
        }catch (Exception var2){
            v2 = find_var(second);
        }
        double result = 0;
        switch (op){
            case "+":
                result = v1 + v2;
                break;
            case "-":
                result = v1 - v2;
                break;
            case "*":
                result = v1 * v2;
                break;
            case "/":
                result = v1 / v2;
                break;

        }
        stack.add(new Token("NUM", String.valueOf(result)));
        if(containVar(var))
            changeVar(var, result);
        else
            var_table.add(new TableOfName( var, result));
    }

    //вычисление логического выражения
    private boolean log_ops(String op, String second, String first) {
        double _first, _second;

        try {
            _first = Double.parseDouble(first);
        } catch (Exception var2) {
            _first = find_var(first);
        }

        try {
            _second = Double.parseDouble(second);
        } catch (Exception var2) {
            _second = find_var(second);
        }

        boolean result = false;

        switch (op) {
            case "==":
                result = _second == _first;
                break;
            case ">":
                result = _first > _second;
                break;
            case "<":
                result = _first < _second;
                break;
            case ">=":
                result = _first >= _second;
                break;
            case "<=":
                result = _first <= _second;
                break;
            case "!=":
                result = _first != _second;
                break;
        }
        return result;

    }
    //возвращает значение переменной var из таблицы переменных
    private double find_var(String var){

        for (TableOfName item : var_table){
            if(item.getName().equals(var))
                return item.getValue();
        }
        return 0;
    }

    // проверяет есть ли переменная var в таблице переменных
    private boolean containVar(String var){
        for (TableOfName item : var_table){
            if(item.getName().equals(var) )
                return true;
        }
        return false;
    }
    //меняет значение переменной var
    private void changeVar(String var, double value){
        for (TableOfName item : var_table){
            if(item.getName().equals(var))
                item.setValue(value);
        }
    }
}
