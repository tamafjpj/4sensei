package service;

import model.Token;

import java.util.LinkedList;
import java.util.Queue;

public class Parser {
    private Token token;
    private Queue<Token> tokens = new LinkedList<>();

    void parse(Queue<Token> tokens) throws Exception {

        this.tokens = tokens;

        while(!tokens.isEmpty()) {
            lang();
        }
        System.out.println("Все проверки пройдены");
    }

    void match() {
        token = tokens.peek();
    }

    void lang() throws Exception {
        expr();
    }

    void expr() throws Exception {
        try{
            assign_expr();
        }
        catch (Exception var){
        }

        try{
            while_expr();
        }
        catch (Exception var){
        }
    }

    void assign_expr() throws Exception {
        var();
        assignOp();
        valueExpr();
        end();
    }

    void valueExpr() throws Exception {

        value();

        while(true) {
            try {
                op();
                value();
            } catch (Exception var2) {
                return;
            }
        }
    }

    void while_expr()throws Exception{

        my_while();
        open_br();
        log_expr();
        close_br();
        open_brace();
        expr();
        close_brace();
    }

    void log_expr() throws Exception {
        value();
        log_op();
        value();
    }

    void value() throws Exception {
        match();
        if (!token.type.equals("VAR") && !token.type.equals("NUM")) {
            throw new Exception("ошибка в методе value " + token.type + " " + token.token);
        }
        token = tokens.poll();
    }

    void var() throws Exception {
        match();
        if (!token.type.equals("VAR")) {
            throw new Exception("ошибка в методе VAR " + token.type + " " + token.token);
        }
        token = tokens.poll();
    }

    void assignOp() throws Exception {
        match();
        if (!token.type.equals("ASSIGN_OP")) {
            throw new Exception("ошибка в методе assignOp " + token.type + " " + token.token);
        }
        token = tokens.poll();
    }

    void op() throws Exception {
        match();
        if (!token.type.equals("OP")) {
            throw new Exception("ошибка в методе op " + token.type + " " + token.token);
        }
        token = tokens.poll();
    }

    void end() throws Exception {
        match();
        if (!token.type.equals("END")) {
            throw new Exception("ошибка в методе end " + token.type + " " + token.token);
        }
        token = tokens.poll();
    }

    void my_while() throws Exception {
        match();
        if (!token.type.equals("WHILE")) {
            throw new Exception("ошибка в методе while " + token.type + " " + token.token);
        }
        token = tokens.poll();
    }

    void open_br() throws Exception {
        match();
        if (!token.type.equals("OPEN_BR")) {
            throw new Exception("ошибка в методе open_br " + token.type + " " + token.token);
        }
        token = tokens.poll();
    }

    void close_br() throws Exception {
        match();
        if (!token.type.equals("CLOSE_BR")) {
            throw new Exception("ошибка в методе close_br " + token.type + " " + token.token);
        }
        token = tokens.poll();
    }

    void open_brace() throws Exception {
        match();
        if (!token.type.equals("OPEN_BRACE")) {
            throw new Exception("ошибка в методе open_brace " + token.type + " " + token.token);
        }
        token = tokens.poll();
    }

    void close_brace() throws Exception {
        match();
        if (!token.type.equals("CLOSE_BRACE")) {
            throw new Exception("ошибка в методе close_brace " + token.type + " " + token.token);
        }
        token = tokens.poll();
    }

    void log_op()throws Exception {
        match();
        if (!token.type.equals("LOG_OP")) {
            throw new Exception("ошибка в методе log_op " + token.type + " " + token.token);
        }
        token = tokens.poll();
    }

}