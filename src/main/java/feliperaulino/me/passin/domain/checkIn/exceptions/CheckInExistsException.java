package feliperaulino.me.passin.domain.checkIn.exceptions;

public class CheckInExistsException extends RuntimeException {

  public CheckInExistsException(String message) {
    super(message);
  }

}
