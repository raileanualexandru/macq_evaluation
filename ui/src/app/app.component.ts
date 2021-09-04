import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Horse } from './horse';
import { HorseService } from './horse.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})


export class AppComponent implements OnInit{

  public horses!: Horse[];
  

  constructor(private horseService: HorseService){}

  ngOnInit(){
    this.getAllHorses();
  }
  public getAllHorses(): void{
    this.horseService.getAllHorses().subscribe(
      (response: Horse[]) =>{
        this.horses = response;
      },
    (error: HttpErrorResponse)=>{
      alert(error.message)
    }
    );
  }

}

