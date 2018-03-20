import {
  Component,
  OnInit
} from '@angular/core';
import {selector} from 'rxjs/operator/publish';
import {BookService} from '../services';

@Component({
  selector: 'app-genres',
  styleUrls: ['./genres.component.css'],
  templateUrl: './genres.component.html'
})
export class GenresComponent implements OnInit {

  genres: string[];

  constructor(private bookService: BookService) {
    bookService.getGenres().subscribe(data => {
      if (data) {
        this.genres = JSON.parse(JSON.stringify(data));
      }
    });
  }

  ngOnInit(): void {
  }

  onGenreClick(genre: string) {
    this.bookService.selectGenre(genre);
  }

}
