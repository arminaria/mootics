package de.tu.berlin.control;

/**
 * Set the positions in the parser
 *
 * User: ara
 * Date: 10.12.13
 */
public enum POS {

    time {
        @Override
        public int get() {
            return 0;
        }
    },
    userId {
        @Override
        public int get() {
            return 1;
        }
    },
    action {
        @Override
        public int get() {
            return 2;
        }
    },
    url {
        @Override
        public int get() {
            return 2;
        }
    },
    info {
        @Override
        public int get() {
            return 3;
        }
    },;

    public abstract int get();
}
