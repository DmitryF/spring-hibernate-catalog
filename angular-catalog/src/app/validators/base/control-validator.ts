import {FormControl, ValidatorFn, AbstractControl} from '@angular/forms';

export class ControlValidator {

	static LETTER_LOWER_CASE_ERROR = 'letterslowercase';
	static LETTER_UPPER_CASE_ERROR = 'lettersuppercase';
	static MIN_MAX_LENGTH_ERROR =    'minmaxlength';
	static SPECIA_CHAR_ERROR =       'specialchar';
	static PASSWORD_ERROR =          'password';
	static REQUIRED_ERROR	=          'required';
	static LENGTH_ERROR =            'length';
	static EQUALS_ERROR =            'equals';
	static DIGITS_ERROR =            'digits';
	static EMAIL_ERROR =             'email';

	static email(control: FormControl): { [key: string]: any } {
		const EMAIL_REGEXP = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
		if (control.value) {
		   if (!control.value.match(EMAIL_REGEXP) || control.value.length <= 5) {
		       return { 'email': true };
		   } else {
		       return null;
		   }
		}
	}

	static specialChar(control: FormControl): { [key: string]: any } {
		if (control.value) {
		   if (!control.value.match(/[-!$%^&*()_+|~=`{}\[\]:";#@'<>?,.\/]/)) {
		       return null;
		   } else {
		       return { 'specialchar': true };
		   }
		}
	}

	static lettersLowerCase(control: FormControl): { [key: string]: any } {
		if (control.value) {
		   if (!control.value.match(/^(?=.*[a-z])/)) {
		       return { 'letterslowercase': true };
		   } else {
		      return null;
		   }
		}
	}

	static lettersUpperCase(control: FormControl): { [key: string]: any } {
		if (control.value) {
		   if (!control.value.match(/^(?=.*[A-Z])/)) {
		       return { 'lettersuppercase': true };
		   } else {
		      return null;
		   }
		}
	}

	static digits(control: FormControl): { [key: string]: any } {
		if (control.value) {
		   if (!control.value.match(/^(?=.*[0-9])/)) {
		       return { 'digits': true };
		   } else {
		      return null;
		   }
		}
	}

	static equals(controlForEquals: FormControl): ValidatorFn {
		return (control: AbstractControl): {[key: string]: any} => {
			if (!(control.value === controlForEquals.value)) {
				return { 'equals': true};
			} else {
				return null;
			}
		};
	}

	static minMaxLength(minLength: Number, maxLength: Number): ValidatorFn {
		return (control: AbstractControl): {[key: string]: any} => {
			if (control.value == null || control.value.length === 0) {
                return null;
            }
            const lengthControlValue: Number = control.value ? control.value.length : 0;
            if (minLength <= lengthControlValue && lengthControlValue <= maxLength) {
              return null;
            } else {
            	return { 'minmaxlength': true};
            }
		};
	}
 }
