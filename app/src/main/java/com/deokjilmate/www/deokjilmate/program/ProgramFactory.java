package com.deokjilmate.www.deokjilmate.program;

/**
 * Created by ash on 2017-08-16.
 */

public class ProgramFactory {
    public static Program create(String program){
        if (program == null) {
            throw new IllegalArgumentException("there is no program");
        }
        if (program.equals("음악중심")) {
            return new EumAckJumgShim();
        }else if (program.equals("인기가요")) {
            return new Ingigayo();
        }else if (program.equals("엠카운트다운")) {
            return new MCountDown();
        } else if (program.equals("뮤직뱅크")) {
            return new MusicBank();
        } else if (program.equals("쇼챔피언")) {
            return new ShowChampion();
        } else if (program.equals("더쇼")) {
            return new ShowChampion();
        }else{
            return null;
        }
    }
}
