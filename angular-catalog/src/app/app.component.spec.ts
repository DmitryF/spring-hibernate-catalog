import { TestBed, async } from '@angular/core/testing';
import { AppComponent } from './app.component';
import {NavBarComponent} from './components/nav-bar/nav-bar.component';
import {FooterComponent} from './components/footer/footer.component';
import {GenresComponent} from './genres/genres.component';
import {
  LoginService, 
  BookService,
  ConfigService,
  ApiService
} from './services';
import {Http, HttpModule, BaseRequestOptions, XHRBackend} from '@angular/http';
import { HttpClient, HttpHeaders, HttpResponse, HttpRequest, HttpEventType, HttpParams, HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { MockBackend } from '@angular/http/testing';
import { MaterialModule } from './app.module';

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        MaterialModule,
        HttpModule,
        HttpClientModule
      ],
      declarations: [
        AppComponent,
        NavBarComponent,
        FooterComponent,
        GenresComponent
      ],
      providers: [
        { provide: XHRBackend, useClass: MockBackend },
        LoginService,
        BookService,
        ConfigService,
        ApiService
      ]
    }).compileComponents();
  }));

  it('should create the AppComponent', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));

  it(`should have NabBarComponent`, async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.nativeElement;
    const navBarComponent = app.querySelector('app-nav-bar');
    expect(navBarComponent).not.toBeNull();
  }));

  it(`should have GenresComponent`, async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.nativeElement;
    const genresComponent = app.querySelector('app-genres');
    expect(genresComponent).not.toBeNull();
  }));

  it(`should have ContentComponent`, async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.nativeElement;
    const contentComponent = app.querySelector('router-outlet');
    expect(contentComponent).not.toBeNull();
  }));

  it(`should have FooterComponent`, async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.nativeElement;
    const footerComponent = app.querySelector('app-footer');
    expect(footerComponent).not.toBeNull();
  }));
  
  /*it('should render title in a h1 tag', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('Welcome to app!');
  }));*/
});
