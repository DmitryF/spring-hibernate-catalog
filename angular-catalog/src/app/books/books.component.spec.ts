import { TestBed, async } from '@angular/core/testing';
import {BooksComponent} from './books.component';
import {BookComponent} from './book/book.component';
import {
  BookService,
  ConfigService,
  ApiService
} from '../services';
import { HttpModule} from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from '../app.module';
import { ActivatedRoute, Router } from '@angular/router';

class MockRouter {
  navigate = jasmine.createSpy('navigate');
}

describe('BooksComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        MaterialModule,
        HttpModule,
        HttpClientModule
      ],
      declarations: [
        BooksComponent,
        BookComponent
      ],
      providers: [
        {provide: Router, useClass: MockRouter},
        {provide: ActivatedRoute, useClass: MockRouter},
        BookService,
        ConfigService,
        ApiService
      ]
    }).compileComponents();
  }));

  it('should create the BooksComponent', async(() => {
    const fixture = TestBed.createComponent(BooksComponent);
    const booksComponent = fixture.debugElement.componentInstance;
    expect(booksComponent).toBeTruthy();
  }));

  it(`should have BookComponent`, async(() => {
    const fixture = TestBed.createComponent(BooksComponent);
    const app = fixture.debugElement.nativeElement;
    const bookComponent = app.querySelector('mat-grid-list');
    expect(bookComponent).not.toBeNull();
  }));

});
