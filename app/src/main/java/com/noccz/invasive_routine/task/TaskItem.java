package com.noccz.invasive_routine.task;

public class TaskItem {
    private int id;
    private String time;
    private String content;
    private boolean isCompleted;

    private TaskItem(Builder builder) {
        id = builder.id;
        time = builder.time;
        content = builder.content;
        isCompleted = builder.isCompleted;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }
    
    public boolean isCompleted() {
        return isCompleted;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder newBuilderWithCurrent() {
        return new Builder(this);
    }

    public static class Builder {
        private int id;
        private String time;
        private String content;
        private boolean isCompleted;

        Builder() {
            // Intentionally empty
        }

        private Builder(TaskItem original) {
            time = original.time;
            content = original.content;
            isCompleted = original.isCompleted;
        }

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withTime(String time) {
            this.time = time;
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }
        
        public Builder withCompleted(boolean isCompleted) {
            this.isCompleted = isCompleted;
            return this;
        }

        TaskItem build() {
            return new TaskItem(this);
        }
    }
}
