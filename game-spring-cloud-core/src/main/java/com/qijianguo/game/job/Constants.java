package com.qijianguo.game.job;

/**
 * @author qijianguo
 */
public class Constants {

    public static class Game {
        public static final String MAP_KEY_GAME = "GAME";
        public static final String JOB_NAME_PREFIX = "GAME_";

        public static String jobName(int gameId) {
            return JOB_NAME_PREFIX + gameId;
        }
    }

    public static class Task {
        public static final String MAP_KEY_TASK = "TASK";
        public static final String MAP_KEY_GAME_NAME = "GAME_NAME";
        public static final String JOB_NAME_PREFIX = "TASK_";
        public static final String JOB_NAME_PATTERN = "TASK_%d_%d";

        public static String jobName(int gameId, long period) {
            return String.format(JOB_NAME_PATTERN, gameId, period);
        }

        public static String groupName() {
            return MAP_KEY_TASK;
        }

    }

}
