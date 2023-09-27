import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AdminOverviewComponent } from 'src/app/shared/components/admin-overview/admin-overview.component';

@Component({
  selector: 'app-home-admin',
  templateUrl: './home-admin.component.html',
  styleUrls: ['./home-admin.component.scss']
})
export class HomeAdminComponent {

  constructor(
    private dialog: MatDialog
  ) { }

  ngOnInit() {

  }

  openAdminDialog() {
    this.dialog.open(AdminOverviewComponent, {
      width: '600px',
      maxWidth: 'calc(100vw - 48px)',
      maxHeight: 'calc(100vh - 48px)',
    });
  }
}
