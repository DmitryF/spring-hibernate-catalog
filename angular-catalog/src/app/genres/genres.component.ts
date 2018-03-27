import {
  Component,
  OnInit
} from '@angular/core';
import {selector} from 'rxjs/operator/publish';
import {BookService} from '../services';
import {Genre} from '../models/genre';

@Component({
  selector: 'app-genres',
  styleUrls: ['./genres.component.css'],
  templateUrl: './genres.component.html'
})
export class GenresComponent implements OnInit {

  genres: Genre[];

  constructor(private bookService: BookService) {
    bookService.getAllGenres().subscribe(data => {
      if (data) {
        this.genres = JSON.parse(JSON.stringify(data));
      }
    });
  }

  ngOnInit(): void {
  }

  /**
   * Change selected genre
   * @param {string} genre
   */
  onGenreClick(genre: string) {    
    this.bookService.selectGenre(genre);
  }

}
