package service;

import model.Token;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Polis {
    public List<Token> polis = new LinkedList<>();
    public Queue<Token> tokens;
    public Token token;

    Polis(Queue<Token> tokens){
        this.tokens = tokens;
    }

    List<Token> setPolis(){
        while (!tokens.isEmpty()){expr();}
        return polis;
    }

    // обработчик выражения
    private void expr(){
        token = tokens.poll();
        if(token.type.equals("VAR")){var();}
        if(token.type.equals("WHILE")){loop();}
    }
    // обработчик переменной/числа
    private void var(){
        polis.add(token);             //добавили переменную
        polis.add(tokens.poll());     //добавили =
        polis.add(tokens.poll());     //добавили 1 элемент правой части
        token = tokens.peek();
        while (!token.type.equals("END")){                       //если в правая честь не заканчивается после 1 элемента
            token = tokens.poll();
            polis.add(tokens.poll());
            polis.add(token);
            token = tokens.peek();
        }
        token = tokens.poll();
    }

    // обработчик цикла
    private void loop(){

        int loop_start = polis.size();
        log_op();
        token = tokens.poll();              //открывающая скобка цикла
        int p = p();
        polis.add(new Token("JUMP", "" + (p-1)));
        polis.add(new Token("IF", "!F"));
        expr();
        polis.add(new Token("JUMP", "" + (loop_start-1)));
        polis.add(new Token("IF", "!F"));
        token = tokens.poll();              //закрывающая скобка цикла
    }
    // обработчик логической операции
    private void log_op(){
        token = tokens.poll();          //открывающая скобка лог выражения
        polis.add(tokens.poll()); //1 элемент лог выражения
        token = tokens.poll();          //лог операция
        polis.add(tokens.poll()); //2 элемент лог выражения
        polis.add(token);

        token = tokens.peek();
        while (!token.type.equals("CLOSE_BR")){
            token = tokens.poll();
            polis.add(tokens.poll());
            polis.add(token);
            token = tokens.peek();
        }
        token = tokens.poll();          //закрывающая скобка лог выражения
    }

    // индекс перехода в цикле
    private int p(){
        int p = polis.size();
        Queue<Token> newtokens = new LinkedList<>(tokens);
        Token newtoken = newtokens.poll();
        while (!newtoken.type.equals("CLOSE_BRACE")){
            newtoken = newtokens.poll();
            p++;
        }
        p++;
        return p;
    }
}
