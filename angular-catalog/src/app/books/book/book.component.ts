import {
  Component,
  Input,
  OnInit
} from '@angular/core';
import {DomSanitizer} from '@angular/platform-browser';
import {SafeStyle} from '@angular/platform-browser/src/security/dom_sanitization_service';
import {Book} from '../../models/book';

@Component({
  selector: 'app-book',
  styleUrls: ['./book.component.css'],
  templateUrl: './book.component.html'
})
export class BookComponent implements OnInit {

  @Input('book') book: Book;

  coverImage: SafeStyle = './assets/unicorn_coloring_book.jpg';//'https://material.angular.io/assets/img/examples/shiba2.jpg';

  constructor(private sanitizer: DomSanitizer) {
    this.coverImage = this.sanitizer.bypassSecurityTrustStyle(`url(${this.coverImage})`);
  }

  ngOnInit(): void {
  }

}
