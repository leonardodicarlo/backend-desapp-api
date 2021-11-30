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
            return PaymentReceived;
        }
    },
    PaymentReceived {
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
    };


    public abstract State nextState();

}