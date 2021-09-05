import { HttpErrorResponse } from '@angular/common/http';
import { ThisReceiver } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Horse } from './horse';
import { HorseService } from './horse.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})


export class AppComponent implements OnInit{

  public horses!: Horse[];
  public deleteHorse!: Horse;
  

  constructor(private horseService: HorseService){}

  ngOnInit(){
    this.getAllHorses();
  }


  public getAllHorses(): void{
    this.horseService.getAllHorses().subscribe(
      (response: Horse[]) =>{
        this.horses = response;
        console.log(response[0]._id.$oid); //test
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

//modal open method start
  /*
public onOpenModal(horse: Horse, mode: string): void {
  const container = document.getElementById('main-container');
  const button = document.createElement('button');
  button.type = 'button';
  button.style.display = 'none';
  button.setAttribute('data-toggle', 'modal');
  if (mode === 'add') {
    button.setAttribute('data-target', '#addHorseModal');
  }

  if (mode === 'edit') {
    this.editEmployee = employee;
    button.setAttribute('data-target', '#updateEmployeeModal');
  }
  
  if (mode === 'delete') {
    this.deleteHorse = horse;
    button.setAttribute('data-target', '#deleteHorseModal');
  }
  container.appendChild(button);
  button.click();
}

*/
//modal open method ends


}

