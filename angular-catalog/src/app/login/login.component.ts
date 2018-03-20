import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import {Observable} from 'rxjs/Observable';
import {User} from '../models/user';
import {LoginService} from '../services/login.service';
import {RegisterValidator} from '../validators/register-validator';
import {RegisterFormErrorStateMatcher} from '../register/register.component';

@Component({
	selector: 'app-login',
	styleUrls: ['login.component.css'],
	templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit{
  PASSWORD_ERROR: 	string = RegisterValidator.PASSWORD_ERROR;
  REQUIRED_ERROR:		string = RegisterValidator.REQUIRED_ERROR;
  LENGTH_ERROR: 		string = RegisterValidator.LENGTH_ERROR;

  minLengthUserName = 3;
  maxLengthUserName = 16;

  minLengthPassword = 6;
  maxLengthPassword = 24;

  loginedUser: User;

  isInProgress = false;
  isResponseError = false;
  hidePassword = true;

  loginForm: FormGroup;

  userNameFormControl: FormControl;
  userPasswordFormControl: FormControl;

	private model = new User();
	private currentUserName;

  formControlMatcher: ErrorStateMatcher;

	constructor(private loginService: LoginService) {
		this.currentUserName = localStorage.getItem('currentUserName');
	}

  ngOnInit() {
    this.loginedUser = new User();
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
  }

  private createForm() {

    this.loginForm = new FormGroup({
      userName: this.userNameFormControl,
      password: this.userPasswordFormControl
    })
  }

  isErrorActive(control: FormControl, errorCode: string) {
    return RegisterValidator.isErrorActive(control, errorCode);
  }

	onSubmit() {
		this.loginService.sendCredential(this.model).subscribe(
			data => {
				localStorage.setItem('token', JSON.parse(JSON.stringify(data))._body);
				this.loginService.sendToken(localStorage.getItem('token'), this.model.userName).subscribe(
					responseData => {
						this.currentUserName = this.model.userName;
						localStorage.setItem('currentUserName', this.model.userName);
						this.model.userName = '';
						this.model.password = '';
					},
					error => console.log(error)
				);
			},
			error => console.log(error)
		);
	}
}

export class LoginFormErrorStateMatcher implements ErrorStateMatcher {

  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}
