import {
  Component,
  OnInit
} from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormGroupDirective,
  NgForm,
  Validators
} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import {User} from '../models/user';
import {RegisterService} from '../services/register.service';
import {RegisterValidator} from '../validators/register-validator';

@Component({
  selector: 'app-register',
  styleUrls: ['./register.component.css'],
  templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit {
  PASSWORD_ERROR: 	string = RegisterValidator.PASSWORD_ERROR;
  REQUIRED_ERROR:		string = RegisterValidator.REQUIRED_ERROR;
  LENGTH_ERROR: 		string = RegisterValidator.LENGTH_ERROR;
  EQUALS_ERROR: 		string = RegisterValidator.EQUALS_ERROR;
  EMAIL_ERROR: 		string = RegisterValidator.EMAIL_ERROR;

  minLengthUserName = 3;
  maxLengthUserName = 16;

	minLengthPassword = 6;
	maxLengthPassword = 24;

	newUser: User;

	isInProgress = false;
	isResponseError = false;
	registered = false;
	hidePassword = true;

	registerForm: FormGroup;

	userNameFormControl: FormControl;
	userPasswordFormControl: FormControl;
	userPasswordConfirmFormControl: FormControl;
	emailFormControl: FormControl;

	formControlMatcher: ErrorStateMatcher;

	constructor(private registerService: RegisterService) {

	}

	ngOnInit() {
		this.newUser = new User();
		this.createFormControls();
		this.createForm();
	}

	private createFormControls() {

		this.formControlMatcher = new RegisterFormErrorStateMatcher();

		this.userNameFormControl = new FormControl('', [
		Validators.required,
		RegisterValidator.minMaxLength(this.minLengthUserName, this.maxLengthUserName)
		]);

		this.userPasswordFormControl = new FormControl('', [
			Validators.required,
			RegisterValidator.minMaxLength(this.minLengthPassword, this.maxLengthPassword),
			RegisterValidator.lettersLowerCase,
			RegisterValidator.lettersUpperCase,
			RegisterValidator.digits
		]);

		this.userPasswordConfirmFormControl = new FormControl('', [
			Validators.required,
			RegisterValidator.minMaxLength(this.minLengthPassword, this.maxLengthPassword),
			RegisterValidator.lettersLowerCase,
			RegisterValidator.lettersUpperCase,
			RegisterValidator.digits,
			RegisterValidator.equals(this.userPasswordFormControl)
		]);

		this.emailFormControl = new FormControl('', [
			Validators.required,
			RegisterValidator.email
		]);
	}

	private createForm() {

		this.registerForm = new FormGroup({
			userName: this.userNameFormControl,
			email: this.emailFormControl,
			password: this.userPasswordFormControl,
			passwordConfirm: this.userPasswordConfirmFormControl
		})
	}

	isErrorActive(control: FormControl, errorCode: string) {
		return RegisterValidator.isErrorActive(control, errorCode);
	}

	onSubmit() {
		console.log('sunmit test: ' + JSON.stringify(this.newUser));
		this.isInProgress = true;
		this.isResponseError = false;
		this.registerService.sendUser(this.newUser)
		.subscribe(
			data => {
				this.registered = true;
				this.newUser = new User();
			},
			error => {
				this.isInProgress = false;
				this.isResponseError = true;
				console.log(error)
			}
		)
	}
}

export class RegisterFormErrorStateMatcher implements ErrorStateMatcher {

  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}
