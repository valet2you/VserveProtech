package com.viralops.touchlessfoodordering.Model;

public class Connect_Header {
    private Data data;

    private String message;

    public Data getData ()
    {
        return data;
    }

    public void setData (Data data)
    {
        this.data = data;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", message = "+message+"]";
    }
    public class Data
    {
        private String cleared_order;

        private String today_order;

        private String dispatched_order;

        private String new_order;

        private String accepted_order;

        public String getCleared_order ()
        {
            return cleared_order;
        }

        public void setCleared_order (String cleared_order)
        {
            this.cleared_order = cleared_order;
        }

        public String getToday_order ()
        {
            return today_order;
        }

        public void setToday_order (String today_order)
        {
            this.today_order = today_order;
        }

        public String getDispatched_order ()
        {
            return dispatched_order;
        }

        public void setDispatched_order (String dispatched_order)
        {
            this.dispatched_order = dispatched_order;
        }

        public String getNew_order ()
        {
            return new_order;
        }

        public void setNew_order (String new_order)
        {
            this.new_order = new_order;
        }

        public String getAccepted_order ()
        {
            return accepted_order;
        }

        public void setAccepted_order (String accepted_order)
        {
            this.accepted_order = accepted_order;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [cleared_order = "+cleared_order+", today_order = "+today_order+", dispatched_order = "+dispatched_order+", new_order = "+new_order+", accepted_order = "+accepted_order+"]";
        }
    }


}
