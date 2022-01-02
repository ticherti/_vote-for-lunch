package com.github.ticherti.voteforlunch.exceptions;

public class TooLateToVoteException extends RuntimeException{
    public TooLateToVoteException(String message){
        super(message);
    }
}
