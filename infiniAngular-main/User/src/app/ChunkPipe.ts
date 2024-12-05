import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'chunkPipe'
})
export class ChunkPipe implements PipeTransform {
  transform(arr: any[], size: number): any[] {
    return arr.reduce((acc, curr, index) => {
      const chunkIndex = Math.floor(index / size);
      if (!acc[chunkIndex]) {
        acc[chunkIndex] = [];
      }
      acc[chunkIndex].push(curr);
      return acc;
    }, []);
  }
}
