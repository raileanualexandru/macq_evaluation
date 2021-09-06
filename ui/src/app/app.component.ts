import { HttpErrorResponse } from '@angular/common/http';
import { ThisReceiver } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Horse } from './horse';
import { HorseService } from './horse.service';
import { User } from './user';
import { UserService } from './user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})


export class AppComponent implements OnInit{

  public horses!: Horse[];
  public user!: User;
  
  constructor(private horseService: HorseService, private userService:UserService){}

  ngOnInit(){
    this.getAllHorses();
  }

  public onAddUser(addForm: NgForm): void{
    document.getElementById("add-user-form")?.click();
    this.userService.addUser(addForm.value).subscribe(
    (response: User[])=>{
      console.log(response);
    },
    (error:HttpErrorResponse) =>{
      alert(error.message)
    }
    )
  }
  
  public getAllHorses(): void{
    this.horseService.getAllHorses().subscribe(
      (response: Horse[]) =>{
        this.horses = response;
        //console.log(response[0]._id.$oid); //test
      },
    (error: HttpErrorResponse)=>{
      alert(error.message)
    }
    );
  }

  

public onAddHorse(addForm: NgForm): void{
  document.getElementById("add-horse-form")?.click();
  this.horseService.addHorse(addForm.value).subscribe(
  (response: Horse[])=>{
    console.log(response);
    this.getAllHorses();
  },
  (error:HttpErrorResponse) =>{
    alert(error.message)
  }
  )
}


public onDeleteHorse(horseId: String): void{
  this.horseService.deleteHorse(horseId).subscribe(
  (response: void) => {
    console.log(response);
    this.getAllHorses();
  },
  (error:HttpErrorResponse) =>{
    alert(error.message)
  }
  )
}

public onUpdateHorse(horseId: String ,horse: Horse): void {
  this.horseService.updateHorse(horseId, horse).subscribe(
    (response: any) => {
      console.log(response);
      //window.location.reload();
      this.getAllHorses();
    },
    (error: HttpErrorResponse) => {
      alert(error.message);
    }
  );
}


}

