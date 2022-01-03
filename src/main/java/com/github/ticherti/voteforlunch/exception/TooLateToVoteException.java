package com.github.ticherti.voteforlunch.exception;

public class TooLateToVoteException extends RuntimeException{
    public TooLateToVoteException(String message){
        super(message);
    }
}
