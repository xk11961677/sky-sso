package com.zyk.sso.client.common.exception;


final public class UserError {
    private UserError(){};

    public static class Error{
        private String _code;
        private String _level;
        private String _desc;
        private Object _attachment;

        public Error(String code,String level,String desc){
            _code = code;
            _level = level;
            _desc = desc;
            _attachment = null;
        }

        /*
         * get error code,length <= 4.
         */
        public String getCode() {return _code;}

        /*
         * get error level
         */
        public String getLevel() {return _level;}

        /*
         * get error description
         */
        public String getDesc() {return _desc;}

        /*
         * attach an object to this Error instance
         */
        public void attach(Object attachment) {
            _attachment = attachment;
        }

        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append(_code)
              .append("[")
              .append(_level)
              .append("]:")
              .append(_desc);

             if(_attachment!=null && !_attachment.toString().isEmpty()) {
                 sb.append(",=>");
                 sb.append(_attachment.toString());
                 sb.append(".");
             }
            return sb.toString();
        }
    }


}
