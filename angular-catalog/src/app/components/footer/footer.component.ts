import {
  Component,
  OnInit
} from '@angular/core';
import {selector} from 'rxjs/operator/publish';

@Component({
  selector: 'app-footer',
  styleUrls: ['./footer.component.css'],
  templateUrl: './footer.component.html'
})
export class FooterComponent implements OnInit {


  constructor() {

  }

  ngOnInit(): void {
  }

}
