package ar.edu.unq.desapp.grupoI.backenddesappapi.model;

public enum State {
    Open {
        @Override
        public State nextState() {
            return PendingPayment;
        }
    },
    PendingPayment {
        @Override
        public State nextState() {
            return PaymentMade;
        }
    },
    PaymentMade {
        @Override
        public State nextState() {
            return Closed;
        }
    },
    Closed {
        @Override
        public State nextState() {
            return Closed;
        }
        @Override
        public State cancel() {
            return Closed;
        }
    },
    Cancelled {
        @Override
        public State nextState() {
            return Cancelled;
        }
    };


    public abstract State nextState();
    public State cancel(){
        return Cancelled;
    }

}