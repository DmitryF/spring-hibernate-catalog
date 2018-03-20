import {
  Component,
  OnInit
} from '@angular/core';
import {selector} from 'rxjs/operator/publish';
import {BookService} from '../services';
import {Book} from '../models/book';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-books',
  styleUrls: ['./books.component.css'],
  templateUrl: './books.component.html'
})
export class BooksComponent implements OnInit {

  books: Book[];

  private subscriberGenre: any;

  private currentGenre: string;

  constructor(private bookService: BookService, private router: Router, private activateRouter: ActivatedRoute) {
    bookService.genreChanged.subscribe(genre => {
      this.router.navigate([`/app-books`, genre]);
    });
  }

  ngOnInit(): void {
    this.subscriberGenre = this.activateRouter.params.subscribe(params => {
      this.currentGenre = params['genre'];
      console.log(this.currentGenre);
      if (this.currentGenre === 'all') {
        this.bookService.getBooks().subscribe(data => {
          if (data) {
            this.books = JSON.parse(JSON.stringify(data));
          }
        });
      } else {
        this.bookService.getBooksByGenre(this.currentGenre).subscribe(data => {
          if (data) {
            this.books = JSON.parse(JSON.stringify(data));
          }
        });
      }
    });
  }

}
