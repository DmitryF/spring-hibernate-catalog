import {FormControl} from '@angular/forms';
import {ControlValidator} from './base/control-validator';

export class RegisterValidator extends ControlValidator{

	public static isErrorActive(control: FormControl, errorCode: string){

		if (errorCode === RegisterValidator.REQUIRED_ERROR) {
			return !this.isRequiredValid(control);
		} else if (!this.isRequiredValid(control)){
			return false;
		}

		if (errorCode === RegisterValidator.LENGTH_ERROR) {
		  return !this.isLengthValid(control);
		} else if (!this.isLengthValid(control)) {
			return false;
		}

		if (errorCode === RegisterValidator.EMAIL_ERROR) {
			return !this.isEmailValid(control);
		} else if (!this.isEmailValid(control)){
			return false;
		}

		if (errorCode === RegisterValidator.PASSWORD_ERROR) {
			return !this.isPasswordSymbolsValid(control);
		} else if (!this.isPasswordSymbolsValid(control)){
			return false;
		}

		if (errorCode === RegisterValidator.EQUALS_ERROR){
			return !this.isEqualValid(control);
		} else if (!this.isEqualValid(control)){
			return false;
		}
	}

	private static isPasswordSymbolsValid(control: FormControl) {
		return !(control.hasError(RegisterValidator.LETTER_LOWER_CASE_ERROR) ||
				control.hasError(RegisterValidator.LETTER_UPPER_CASE_ERROR) ||
				control.hasError(RegisterValidator.DIGITS_ERROR));
	}

	private static isEmailValid(control: FormControl) {
		return !(control.hasError(RegisterValidator.EMAIL_ERROR));
	}

	private static isLengthValid(control: FormControl) {
		return !(control.hasError(RegisterValidator.MIN_MAX_LENGTH_ERROR));
	}

	private static isRequiredValid(control: FormControl) {
		return !(control.hasError(RegisterValidator.REQUIRED_ERROR));
	}

	private static isEqualValid(control: FormControl) {
		return !(control.hasError(RegisterValidator.EQUALS_ERROR));
	}
}
