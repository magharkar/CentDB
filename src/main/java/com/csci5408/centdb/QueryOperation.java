package com.csci5408.centdb;



public enum QueryOperation {

    SELECT {
        public <T, J> T accept(QueryOperationVisitor<T, J> visitor, J data) {
            return visitor.visitSelect(data);
        }
    },
    UPDATE {
        public <T, J> T accept(QueryOperationVisitor<T, J> visitor, J data) {
            return visitor.visitUpdate(data);
        }
    },
    DELETE {
        public <T, J> T accept(QueryOperationVisitor<T, J> visitor, J data) {
            return visitor.visitDelete(data);
        }
    },
    DROP {
        public <T, J> T accept(QueryOperationVisitor<T, J> visitor, J data) {
            return visitor.visitDrop(data);
        }
    },
    INSERT {
        public <T, J> T accept(QueryOperationVisitor<T, J> visitor, J data) {
            return visitor.visitInsert(data);
        }
    },
    CREATE {
        public <T, J> T accept(QueryOperationVisitor<T, J> visitor, J data) {
            return visitor.visitCreate(data);
        }
    };

    public abstract <T, J> T accept(QueryOperationVisitor<T, J> visitor, J data);
    public interface QueryOperationVisitor<T, J> {
        T visitSelect(J data);
        T visitUpdate(J data);
        T visitDelete(J data);
        T visitDrop(J data);
        T visitInsert(J data);
        T visitCreate(J data);
    }
}




