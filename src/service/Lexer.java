package service;

import model.Lexem;
import model.Token;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    public static void main(String[] args) throws Exception {
        //lexer here
        Lexer lexer = new Lexer();
        String test =   "a = 10 + 25  - 31; b = a + 10; b = 25 * 2; _while(a<100) { a = a + 5;}  _while(b<500) { b = b + 75; }";
        Queue<Token> tokens;
        try {
            tokens = lexer.findTokens(test.replaceAll("\\s",""));
        }catch (Exception e){e.printStackTrace();return;}

        for (Token item : tokens){
            System.out.println(item.type + " " + item.token);
        }

        //polis here

        Queue<Token> newTokens = new LinkedList<>(tokens);
        Parser parser = new Parser();
        parser.parse(tokens);
        Polis p = new Polis(newTokens);
        List<Token> polis;
        polis = p.setPolis();

        for (int i = 0; i<polis.size(); i++){
            Token item = polis.get(i);
            System.out.println(i + " " + item.type + " " + item.token);
        }
        calcPolis cp = new calcPolis(polis);
        cp.calc_polis();



    }
    private Queue<Token> findTokens(String exp)throws Exception{
        Queue<Token> tokens = new LinkedList<>();
        List<Lexem> lexems = Arrays.asList(Lexem.values());
        while(!exp.isEmpty()) {
            Iterator<Lexem> it = lexems.iterator();
            while(it.hasNext()) {
                Lexem l = it.next();
                Matcher m = l.getPattern().matcher(exp);
                if (m.find()) {
                    tokens.add(new Token(l.name(), m.group()));
                    exp = exp.replaceFirst(Pattern.quote(m.group()), "");
                    break;
                }
                if(!it.hasNext()){
                    throw  new Exception("NOT VALID");
                }
            }
        }
        return tokens;
    }
}
