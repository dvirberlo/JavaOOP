import java.util.Map;

class MainTest {
    public static void main(String[] args) {
        try {
            Expression x = new Var("x"), y = new Var("y");
            Expression z = new Var("x & y");

            Expression xAndY = new And(x, y);
            Expression yAndX = new And(y, x);
            Expression xOrY = new Or(x, y);

            // Expression t = new Or(xAndY, yAndX);
            Expression t = new Xor(x, x);
            System.out.println(t);
            System.out.println(t.evaluate(Map.of("x", true, "y", false)));
            System.out.println(t.simplify());
            System.out.println(xAndY.equals(yAndX));
            System.out.println(xAndY.equals(xOrY));
            System.out.println(xAndY.equals(z));

            // Expression e1 = new And(new Var("x"), new Not(new Var("y")));
            // System.out.println(e1);
            // System.out.println(e1.getVariables());
            // System.out.println(e1.evaluate(Map.of("x", true, "y", false)));
            //
            // Expression e2 = e1.assign("x", new And(new Var("y"), Val.TRUE));
            // System.out.println(e2);
            // System.out.println(e2.getVariables());
            // System.out.println(e2.evaluate(Map.of("y", true)));


        } catch (Exception ignored) {
        }
    }
}
