// so senario is like we are a platyform like leetcode where we submit the our solutions so we need to know how 
// many submissions are done for a single problem.


// 1. Eager Loading.
class JudgeAnalytics{
    private static final JudgeAnalytics judgeAnalytics = new JudgeAnalytics();

    private JudgeAnalytics(){
        // private constructor..
    }

    public static JudgeAnalytics getInstance(){
        return judgeAnalytics;
    }
}


// 2. Lazy Loading...

// The lazy loading helps us to load resources when we want but the issues is when multiple threads try to create the instance.
// we might end up creating multiple instances...
// so we need to handle race condition cases
// we have 3 ways to handle this they are :

// 1. Synchronize kwyword
// 2. double check box --> volatile keyword
// 3. bill push singleton --> prefered for the java 5+ applications..(uses inner classes).


class JudgeAnalytics{
    private static JudgeAnalytics judgeAnalytics;

    private JudgeAnalytics(){
        // private constructor
    }

    public static JudgeAnalytics getInstance(){
        if(judgeAnalytics == null){
            judgeAnalytics = new JudgeAnalytics();
        }
        return judgeAnalytics;
    }
}


// Multhithreading handling cases:

// This can handle race conditions but not optimized way due to mutual excusion locks..
// and we might use the get method multiple times so we might end up synchronizing evey time so not optimal.


class JudgeAnalytics{
    private static JudgeAnalytics judgeAnalytics;

    private JudgeAnalytics(){
        // private constructor
    }

    public static synchronized JudgeAnalytics getInstance(){
        if(judgeAnalytics == null){
            judgeAnalytics = new JudgeAnalytics();
        }
        return judgeAnalytics;
    }
}


// double checking block(volatile)
// volatile solves us the visibility problem for us and using the synchronize keyword will give the me for us.

class JudgeAnalytics{
    private static volatile JudgeAnalytics judgeAnalytics;

    private JudgeAnalytics(){
        // private constructor
    }

    public static  JudgeAnalytics getInstance(){
        if(judgeAnalytics == null){
            synchronized(JudgeAnalytics.class){
                if(judgeAnalytics == null){
                    judgeAnalytics = new JudgeAnalytics();
                }
            }
        }
        return judgeAnalytics;
    }
}

// bill pugh singleton pattern

class JudgeAnalytics{
    

    private JudgeAnalytics(){
        // private constructor
    }

    private static class Holder{
        private static final JudgeAnalytics judgeAnalytics = new JudgeAnalytics();
    }

    public static JudgeAnalytics getInstance(){
       return  Holder.judgeAnalytics;
    }
}




public class Main{
    public static void main(String[] args) {
        
    }
}