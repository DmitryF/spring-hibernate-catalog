import {Genre} from '../models/genre';


export class Book {

  public id: string;
  public name: string;
  public authorName: string;
  public countPages: Number;
  public description: string;
  public language: string;
  public genre: Genre;
}
