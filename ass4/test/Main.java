import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Expression and = new And(new Var("x"), new Var("y"));
        System.out.println("and: " + and);
        System.out.println("and nortify: " + and.norify());
        System.out.println("Should print: ((x V x) V (y V y))\n");
        System.out.println("and nandify: " + and.nandify());
        System.out.println("Should print: ((x A y) A (x A y))\n");
        Expression nand = new Nand(new Var("x"), new Var("y"));
        System.out.println("nand: " + nand);
        System.out.println("nand nortify: " + nand.norify());
        System.out.println("Should  print: (((x V x) V (y V y)) V ((x V x) V (y V y)))\n");
        System.out.println("nand nandtify: " + nand.nandify());
        System.out.println("Should print: (x A y)\n");
        Expression nor = new Nor(new Var("x"), new Var("y"));
        System.out.println("nor: " + nor);
        System.out.println("nor nortify: " + nor.norify());
        System.out.println("Should print: (x V y)\n");
        System.out.println("nor nandtify: " + nor.nandify());
        System.out.println("Should print: (((x A x) A (y A y)) A ((x A x) A (y A y)))\n");
        Expression not = new Not(new Var("x"));
        System.out.println("not: " + not);
        System.out.println("not nortify: " + not.norify());
        System.out.println("Should print: (x V x)\n");
        System.out.println("not nandtify: " + not.nandify());
        System.out.println("Should print: (x A x)\n");
        Expression or = new Or(new Var("x"), new Var("y"));
        System.out.println("or: " + or);
        System.out.println("or nortify: " + or.norify());
        System.out.println("Should print: ((x V y) V (x V y))\n");
        System.out.println("or nandtify: " + or.nandify());
        System.out.println("Should print: ((x A x) A (y A y))\n");
        Expression xnor = new Xnor(new Var("x"), new Var("y"));
        System.out.println("xnor: " + xnor);
        System.out.println("xnor nortify: " + xnor.norify());
        System.out.println("Should print: ((x V (x V y)) V (y V (x V y)))");
        System.out.println("Alternately: ((y V (x V x)) V (x V (y V y)))\n");
        System.out.println("xnor nandtify: " + xnor.nandify());
        System.out.println("Should print: (((x A x) A (y A y)) A (x A y))\n");
        Expression xor = new Xor(new Var("x"), new Var("y"));
        System.out.println("xor: " + xor);
        System.out.println("xor nortify: " + xor.norify());
        System.out.println("Should print: (((x V x) V (y V y)) V (x V y))\n");
        System.out.println("xor nandtify: " + xor.nandify());
        System.out.println("Should print: ((x A (x A y)) A (y A (x A y)))");
        System.out.println("Alternately: ((y A (x A x)) A (x A (y A y)))\n");
        Expression e = new Xnor(new Nand(new Var("x"), new Val(false)),
                new Not(new And(new Or(new Var("x"), new Var("y")),
                        new Xor(new Val(true), new Var("z")))));
        System.out.println(e);
        System.out.println("Should print: ((x A F) # ~((x v y) ^ (T ⊕ z)))\n");
        System.out.println("After simplification: " + e.simplify());
        System.out.println("Should print: (T # ~(((x v y) ^ ~(z))))\n");
        e = e.assign("y", new Val(false));
        System.out.println("After assigning y = false: " + e);
        System.out.println("Should print: ((x A F) # ~((x v F) ^ (T ⊕ z)))\n");
        System.out.println("After simplification: " + e.simplify());
        System.out.println("Should print: (T # ~((x ^ ~(z))))\n");
        Map<String, Boolean> map = new HashMap<>();
        map.put("x", true);
        map.put("z", false);
        try {
            System.out.println("evalute using x = true, z = false: " + e.evaluate(map));
            System.out.println("Should print: false");
        } catch (Exception ignored) {
            System.out.println("Error. Exception thrown during evaluation.");
        }


    }
}
