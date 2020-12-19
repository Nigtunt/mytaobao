import java.util.Stack;

/**
 * @Author: YHQ
 * @Date: 2020/8/27 10:48
 */
public class Myqueue<T> {
    private Stack<T> stack1 =new Stack<>();
    private Stack<T> stack2 =new Stack<>();

    void offer(T a){
        stack1.push(a);
    }
    T poll() {
        peek();
        return stack2.pop();
    }

    T peek(){
        if (stack2.empty()){
            while (!stack1.empty()){
                stack2.push(stack1.pop());
            }
        }
        return stack2.peek();
    }
    static class item{
        int value;
        String label;

        public item(int value, String label) {
            this.value = value;
            this.label = label;
        }
    }
    static void t(item[] num){
        int max=  0;
        for(int i=0;i<num.length;i++){
            if(max<num[i].value){
                max = num[i].value;
            }
        }
        while(max > 0){
            for(int i=0;i<num.length;i++){
                if(max <= num[i].value ) System.out.print('#');
                else System.out.print(' ');
                int cur = num[i].label.length();
                for (int i1 = 0; i1 < cur; i1++) {
                    System.out.print(' ');
                }
            }
            max--;
            System.out.println();
        }
        System.out.println("-------");
        boolean f = false;
        int min = Integer.MAX_VALUE;
        for(int i=0;i<num.length;i++){
            if(num[i].value<0 && min > num[i].value){
                min = num[i].value;
                f = true;
            }
        }
        int cur = 0;
        if (f){
            while(min < 0){
                for(int i=0;i<num.length;i++){
                    if(cur > num[i].value ) System.out.print('#');
                    else System.out.print(' ');

                    int curlen = num[i].label.length();
                    for (int i1 = 0; i1 < curlen; i1++) {
                        System.out.print(' ');
                    }
                }
                cur--;
                min++;
                System.out.println();
            }
        }

        for(int i=0;i<num.length;i++){
            System.out.print(num[i].label+" ");
        }
    }
    public static void main(String args[]){

        t(new item[]{
                new item(-5,"28"),
                new item(3,"abc")
                ,new item(5,"b5"),
                new item(-4,"c"),
                new item(-2,"222"),

                new item(8,"d123")
        });
    }
}
